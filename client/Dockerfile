# Description: Dockerfile for the client side of the application
FROM node:20 as build-stage
# Create app directory
WORKDIR /app

# Install app dependencies
COPY ./package*.json ./

RUN npm install

# Copy the rest of the application
COPY . .

#Build the vue app
RUN npm run build

# Production stage
FROM nginx:stable-alpine as production-stage

# Copy the build files to the nginx container

COPY --from=build-stage /app/dist /usr/share/nginx/html

# Copy the nginx configuration file
COPY ./nginx/default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# Start the nginx server
CMD ["nginx", "-g", "daemon off;"]