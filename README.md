# Restaurant Delivery System

A web-based restaurant ordering and delivery workflow built with a layered architecture (Presentation → Business → Persistence → Helper). Users can log in, browse/search menu items, add items to a cart, place orders, choose a payment method, and view order status.

## Features
- User login flow (login page + login result)
- Browse menu and search menu items
- Add/remove items in cart and view cart total
- Checkout + payment method selection
- Place order and view order status

## Tech Stack
- Java (Servlet/JSP)
- HTML/CSS
- Layered architecture (Business, Persistence, Helper)
- Git/GitHub

## Project Structure
- `Business/` – business logic (order/cart handling, validations, workflows)
- `Persistence/` – database/data access layer (CRUD, queries, storage logic)
- `Helper/` – shared utilities and models (DTOs, helpers, constants)
- `*.jsp / *.html` – UI pages (browse, cart, login, checkout, status)

## How It Works (User Flow)
1. **Login** (`login.html` → `loginResult.jsp`)
2. **Browse/Search Menu** (`browse.jsp`, `browseMenu.jsp`)
3. **Cart** (`viewCart.jsp`)
4. **Checkout / Payment** (`placeOrder.jsp`)
5. **Order Status** (`viewStatus.jsp`)

## Setup / Run (Local)
> Update these steps to match your environment (Tomcat/NetBeans/Eclipse).

1. Clone the repo
   ```bash
   git clone https://github.com/<your-username>/Restaurant-Delivery-System.git
   cd Restaurant-Delivery-System
