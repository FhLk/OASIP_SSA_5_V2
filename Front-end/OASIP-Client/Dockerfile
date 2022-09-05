# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY . .
RUN npm install
COPY . .
RUN npm run build

# production stage
FROM nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html
COPY ./default.conf /etc/nginx/conf.d/default.conf
EXPOSE 3000