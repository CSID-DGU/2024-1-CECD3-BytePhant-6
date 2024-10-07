import aiomysql
from core import config

async def get_db_connection():
    conn = await aiomysql.connect(
        host=config.db_host,
        user=config.db_user,
        password=config.db_password,
        db=config.db_name,
        charset='utf8'
    )
    return conn
