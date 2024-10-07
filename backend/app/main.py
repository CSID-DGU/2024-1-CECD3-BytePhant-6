from fastapi import FastAPI
from routers.conversation import conversation_router

app = FastAPI()

app.include_router(conversation_router, prefix="/conversation")

@app.get('/')
def home():
    return 'This is home!'
