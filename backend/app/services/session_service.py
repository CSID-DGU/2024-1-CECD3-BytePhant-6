from langchain_community.chat_message_histories import ChatMessageHistory
from langchain_core.chat_history import BaseChatMessageHistory

store = {}

def get_history(session_ids: str) -> BaseChatMessageHistory:
    print("session_id : " + session_ids)
    if session_ids not in store:  
        store[session_ids] = ChatMessageHistory()
    return store[session_ids] 