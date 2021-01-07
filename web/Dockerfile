FROM node:lts-alpine

RUN npm install -g vue-cli

COPY www /www

WORKDIR /www

RUN npm install

EXPOSE 8080
CMD [ "npm", "run", "dev" ]
#CMD [ "sh" ]
