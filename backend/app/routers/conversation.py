import json
import copy
import datetime
from core import config
from fastapi import APIRouter, HTTPException
from langchain_openai import ChatOpenAI
from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
from langchain_core.runnables.history import RunnableWithMessageHistory
from langchain.schema import Document
from services.user_service import get_user_info
from services.question_service import generate_question, mark_question
from services.weather_service import get_weather
from services.document_service import query_ensemble, save_chunks_to_file
from services.session_service import get_history
from utils.utils import split_text
from utils.prompts import prompt1, prompt2, prompt3, prompt4
from models.user import UserInput, AnswerInput


conversation_router = APIRouter()


model = ChatOpenAI(model="gpt-4o", temperature=0, openai_api_key=config.openai_api_key)


# test data
name = "박호산"
age = 70
location = "Seoul"

async def final(name, age, location):
    session_id = name + str(age) + location
    original_history = get_history(session_id)
    history_copy = copy.deepcopy(original_history)

    prompt = ChatPromptTemplate.from_messages(
        [
            (
                "system",
                prompt3,
            ),
            MessagesPlaceholder(variable_name="history"),
            ("human", "{input}"),
        ]
    )

    messages = prompt.format_messages(input="", history=history_copy.messages, current_time=datetime.datetime.now().strftime('%Y-%m-%d'))
    response = model.invoke(messages)

    print("\n요약문 : ")
    print(response.content)

    cleaned_text = response.content.replace("{", "").replace("}", "")
    document = Document(page_content=cleaned_text)
    chunks = split_text([document])
    chunks.extend
    
    for chunk in chunks:
        print(chunk)

    save_chunks_to_file(chunks, f"./data/{age}{location}.txt")

    prompt_ = ChatPromptTemplate.from_messages(
        [
            (
                "system",
                prompt4,
            ),
            MessagesPlaceholder(variable_name="history"),
            ("human", "{input}"),
        ]
    )

    messages = prompt_.format_messages(input="", history=history_copy.messages)
    response = model.invoke(messages)

    print("\n관심사 키워드 : ")
    print(response.content)


@conversation_router.post('/first')
async def conversation_first(user_input: UserInput):
    user_id = user_input.user_id

    try:
        user_info = await get_user_info(user_id)
    except Exception as e:
        raise HTTPException(status_code=404, detail=str(e))

    try:
        question = await generate_question(user_id)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

    try:
        weather_info = await get_weather(user_info['location'])
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

    question_id = question['question_id']
    question_text = question['question_text']

    prompt = ChatPromptTemplate.from_messages(
        [
            (
                "system",
                prompt1,
            ),
            MessagesPlaceholder(variable_name="history"),
            ("human", "{input}"),
        ]
    )

    runnable = prompt | model
    with_message_history = RunnableWithMessageHistory(
        runnable, get_history, input_messages_key="input", history_messages_key="history"
    )

    response = with_message_history.invoke(
        {
            "age": user_info['age'], "name": user_info['name'], "location": user_info['location'],
            "weather": weather_info, "question": question_text, "input": ""
        },
        config={"configurable": {"session_id": name + str(age) + location}},
    )

    return {"status": "success", "message": response.content, "question": question_id}



@conversation_router.post('/second')
async def conversation_second(answer_input: AnswerInput):
    answer = answer_input.answer

    prompt = ChatPromptTemplate.from_messages(
        [
            (
                "system",
                prompt2,
            ),
            MessagesPlaceholder(variable_name="history"),
            ("human", "{input}"),
        ]
    )

    runnable2 = prompt | model 

    with_message_history = (
        RunnableWithMessageHistory(
            runnable2,
            get_history,
            input_messages_key="input",
            history_messages_key="history",
        )
    )

    path = str(age) + location
    context_text = await query_ensemble(answer, f"./data/{path}.txt")
    print("context: \n")
    for i in range(len(context_text)):
        print(context_text[i])

    response = with_message_history.invoke(
        {
            "input": answer, 
            "context": context_text,
        },
        config={"configurable": {"session_id": name + str(age) + location}},
    )

    response_json = json.loads(response.content)
    message = response_json.get('message')
    score = response_json.get('score')

    print(message)
    print(score)

    if int(score) <= 5:
        print(f"Score가 {score}로 낮아서 대화를 종료합니다.")

        history = get_history(name + str(age) + location)
        if len(history.messages) >= 2:
            history.messages = history.messages[:-2]
            
        await final(name, age, location)
        return {"status": "success", "message": "지금 대화가 어려우신가봐요. 대화를 종료하겠습니다.", "score": score}
    
    return {"status": "success", "message": message, "score": score}
