FROM node:8-alpine as builder

WORKDIR /usr/src/app

COPY package.json .

RUN yarn install

COPY . .

RUN npm run build

FROM nginx:alpine

COPY --from=builder /usr/src/app/dist/ /usr/share/nginx/html