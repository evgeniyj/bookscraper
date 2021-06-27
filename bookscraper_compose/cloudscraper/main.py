import cloudscraper
from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

app = FastAPI()

scraper = cloudscraper.create_scraper()

@app.get('/')
async def index():
	result = scraper.get("https://www.blinkist.com/en/nc/daily/").text
	return result
