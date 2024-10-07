import os
from dotenv import load_dotenv

load_dotenv()

openai_api_key = os.getenv('OPENAI_API_KEY')
openweather_api_key = os.getenv('OPENWEATHER_API_KEY')
db_password = os.getenv('MYSQL_PASSWORD')
db_user = os.getenv('MYSQL_USER')
db_host = os.getenv('MYSQL_HOST')
db_name = os.getenv('MYSQL_DB')

