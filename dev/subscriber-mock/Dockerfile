FROM node:18-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY index.js ./

EXPOSE 4000

CMD ["node", "index.js"]
