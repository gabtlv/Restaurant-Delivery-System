# Restaurant Delivery System

A cloud-deployed microservices-based restaurant ordering platform built with Java, Docker, and Kubernetes on Google Cloud.

## Tech Stack
Java (Servlet/JSP), MySQL, Docker, Kubernetes (GKE), KubeMQ, JWT Authentication, REST API

## Microservices
- **FrontendService** — User interface, login, browse menu, cart, order
- **MenuService** — Menu items REST API connected to Menu_DB
- **CartService** — Cart management REST API connected to Menu_DB
- **OrderService** — Order processing with KubeMQ messaging

## Databases
- **Users_DB** — User authentication (FrontendService)
- **Menu_DB** — Menu items (MenuService, CartService)

## Deployment
- All services containerized with Docker and pushed to Docker Hub (gabtlv/coe692)
- Deployed on Google Kubernetes Engine with 3 nodes
- Asynchronous messaging via KubeMQ between CartService and OrderService

## Public URL
http://34.19.187.121/FrontendService

## Login
- Email: test@test.com
- Password: 1234
