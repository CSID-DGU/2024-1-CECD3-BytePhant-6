import aiohttp
from core.config import openweather_api_key as api_key

async def get_weather(location):
    weather_api_url = f"http://api.openweathermap.org/data/2.5/weather?q={location}&appid={api_key}&lang=kr&units=metric"
    
    async with aiohttp.ClientSession() as session:
        try:
            async with session.get(weather_api_url) as response:
                response.raise_for_status()
                data = await response.json()
                weather_description = data['weather'][0]['description']
                temperature = data['main']['temp']
                return f"현재 {location}의 날씨는 {weather_description}이며, 기온은 {temperature}도입니다."
        
        except aiohttp.ClientError as e:
            return f"날씨 정보를 불러오는 데 실패했습니다. 오류: {e}"
