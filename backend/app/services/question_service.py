from core.db import get_db_connection
from exceptions import NotExistNewQuestionError

async def generate_question(user_id: str):
    conn = await get_db_connection()

    sql = """
    SELECT q.question_id, q.question_text 
    FROM Questions q 
    LEFT JOIN UserQuestions uq 
    ON q.question_id = uq.question_id AND uq.user_id = %s
    WHERE uq.question_id IS NULL 
    ORDER BY RAND() 
    LIMIT 1;
    """
    try:
        async with conn.cursor() as cur:
            await cur.execute(sql, (user_id,))
            question = await cur.fetchone()
            if question is None:
                raise NotExistNewQuestionError("새로운 질문이 없습니다.")
            return {"question_id": question[0], "question_text": question[1]}
    finally:
        conn.close()


async def mark_question(user_id, question_id):
    conn = await get_db_connection()

    sql = """
        INSERT INTO UserQuestions (user_id, question_id, asked_at)
        VALUES (%s, %s, NOW())
        ON DUPLICATE KEY UPDATE asked_at = NOW();
        """
    try:
        with conn.cursor() as cur:
            record = (user_id, question_id)
            cur.execute(sql, record)
            conn.commit()
            return {"status": "success", "message": f"질문 {question_id}번을 사용자 {user_id}에게 물어봄."}
    except Exception as e:
        print(f"Error: {str(e)}")
        raise HTTPException(status_code=500, detail=str(e))
    finally:
        conn.close()