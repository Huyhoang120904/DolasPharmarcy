FROM node:20-alpine AS build

WORKDIR /client

COPY package*.json ./
COPY ./.env ./

RUN npm ci --prefer-offline --no-audit

COPY . .

RUN npm run build

###
FROM nginx:1.22-alpine

COPY --from=build /client/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
