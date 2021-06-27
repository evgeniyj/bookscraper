FROM python:3.8.8-slim-buster

RUN mkdir /opt/cloudscraper \
	&& mkdir /opt/cloudscraper/app \
	&& cd /opt/cloudscraper \
	&& pip install virtualenv \
	&& virtualenv env \
	&& pip install fastapi \
	&& pip install uvicorn \
	&& pip install cloudscraper 

COPY main.py /opt/cloudscraper/app/main.py

ENTRYPOINT cd /opt/cloudscraper/app && uvicorn main:app --host 0.0.0.0 --port 8000