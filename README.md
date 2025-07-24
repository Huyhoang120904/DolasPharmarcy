# 🏥 Dolas Pharmacy Management System

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19.1.0-blue.svg)](https://reactjs.org/)
[![Vite](https://img.shields.io/badge/Vite-6.2.0-646CFF.svg)](https://vitejs.dev/)
[![MariaDB](https://img.shields.io/badge/MariaDB-Database-brown.svg)](https://mariadb.org/)

A comprehensive pharmacy management system designed to streamline operations for pharmaceutical retailers. This modern web application provides functionality for inventory management, customer accounts, prescription handling, and order processing through an intuitive interface.

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [API Documentation](#-api-documentation)
- [Demo Accounts](#-demo-accounts)
- [Contributing](#-contributing)
- [Contact](#-contact)
- [License](#-license)

## ✨ Features

### 🔐 **Authentication & Security**

- Secure JWT-based authentication system
- Role-based access control (Admin, Staff, Customer)
- Protected routes and API endpoints

### 💊 **Product Management**

- Comprehensive medicine catalog with detailed information
- Advanced variant management system
- Category-based organization
- Real-time inventory tracking

### 🛒 **Order Processing**

- Complete order lifecycle management
- Shopping cart functionality
- Order status tracking
- Order confirmation emails

### 💳 **Payment Integration**

- **VnPay payment gateway** integration
- Secure payment processing

### 📧 **Communication**

- Automated order confirmation emails
- Customer notification system

### 🌐 **Cloud Services**

- **Cloudinary integration** for image storage and management
- Optimized image delivery and transformations

### 👥 **User Management**

- Customer profile management
- Purchase history tracking
- Review and rating system

### 📊 **Analytics & Reporting**

- Sales analytics with interactive charts
- Inventory reports
- Customer insights

## 🛠 Tech Stack

### Frontend

- **React.js** (v19.1.0) - Modern UI library
- **Vite** (v6.2.0) - Fast build tool and development server
- **Ant Design** - Professional UI component library
- **Tailwind CSS** - Utility-first CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client for API requests
- **Recharts** - Data visualization library
- **Leaflet** - Interactive maps

### Backend

- **Spring Boot** (v3.4.5) - Java-based web framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **MapStruct** - Java bean mappings
- **Maven** - Dependency management and build tool

### Database & Storage

- **MariaDB** - Primary database
- **Cloudinary** - Cloud-based image storage

### External Services

- **VnPay** - Payment gateway
- **Email Service** - Order confirmations and notifications

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- **Java 17** or higher
- **Node.js** (v14 or later recommended)
- **npm** or **yarn**
- **MariaDB** server
- **Maven** (for backend build)

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/Huyhoang120904/DolasPharmarcy.git
cd DolasPharmarcy
```

### 2. Backend Setup

#### Navigate to backend directory

```bash
cd backend
```

#### Configure Database

1. Create a MariaDB database named `dolas_pharmacy`
2. Update `application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/dolas_pharmacy
spring.datasource.username=your_username
spring.datasource.password=your_password
```

#### Configure External Services

Update `application.properties` with your service credentials:

```properties
# Cloudinary Configuration
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret

# Email Configuration
spring.mail.username=your_email
spring.mail.password=your_app_password

# VnPay Configuration
vnpay.merchant-id=your_merchant_id
vnpay.secret-key=your_secret_key
```

#### Build and Run Backend

```bash
mvn clean install
mvn spring-boot:run
```

The backend server will start on `http://localhost:8080`

### 3. Frontend Setup

#### Navigate to frontend directory

```bash
cd ../frontend
```

#### Install Dependencies

```bash
npm install
```

#### Start Development Server

```bash
npm run dev
```

The frontend application will start on `http://localhost:5173`

## ⚙️ Configuration

### Environment Variables

Create a `.env` file in the frontend directory:

```env
VITE_API_BASE_URL=http://localhost:8080/api
VITE_CLOUDINARY_CLOUD_NAME=your_cloud_name
```

### Database Migration

The application uses Spring Boot's automatic database schema creation. On first run, the database tables will be created automatically.

## 📱 Usage

1. **Access the Application**: Navigate to `http://localhost:5173`
2. **Login**: Use the demo accounts provided below
3. **Explore Features**:
   - Browse medicine catalog
   - Add items to cart
   - Place orders with VnPay payment
   - Manage inventory (Admin/Staff)
   - View analytics and reports (Admin)

## 📚 API Documentation

The backend provides RESTful APIs for:

### Authentication Endpoints

- `POST /api/auth/login` - User authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/logout` - User logout

### Product Management

- `GET /api/products` - Retrieve products with pagination
- `GET /api/products/{slug}` - Get product by slug
- `POST /api/products` - Create new product (Admin/Staff)
- `PUT /api/products/{id}` - Update product (Admin/Staff)
- `DELETE /api/products/{id}` - Delete product (Admin)

### Order Management

- `GET /api/orders` - Get user orders
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}/status` - Update order status (Staff/Admin)

### Payment

- `POST /api/payments/vnpay/create` - Create VnPay payment
- `GET /api/payments/vnpay/callback` - Handle payment callback

## 🔑 Demo Accounts

### Administrator Account

- **Username**: `admin`
- **Password**: `admin123`
- **Email**: `hoang123@yopmail.com`
- **Access**: Full system access, analytics, user management

### Customer Account

- **Username**: `huyhoang`
- **Password**: `huyhoang123`
- **Email**: `hoang1234@yopmail.com`
- **Access**: Shopping, order history, profile management

> **Note**: Demo emails use [Yopmail](https://yopmail.com/) temporary email service for testing purposes.

## 🎯 Project Status

**Current Status**: 🚧 **In Development**

This project is actively being developed as a learning exercise and portfolio project. New features and improvements are regularly added.

### Upcoming Features

- Mobile responsive design improvements
- Advanced reporting dashboard
- Prescription verification system
- Multi-language support

## 🤝 Contributing

This is a personal learning project, but feedback and suggestions are welcome!

### Development Team

- **Lead Developer**: Huy Hoang - Backend & System Architecture
- **Frontend Assistant**: Nguyen Quang Huy - UI/UX Implementation

### Learning Resources

This project was developed using knowledge from:

- YouTube tutorials and documentation
- Spring Boot and React official guides
- Community best practices and open-source examples

## 📞 Contact

### Developer

- **Name**: Huy Hoang
- **Email**: [hoangbeo1124@gmail.com](mailto:hoangbeo1124@gmail.com)
- **Facebook**: [Connect on Facebook](https://facebook.com/your-profile)
- **GitHub**: [@Huyhoang120904](https://github.com/Huyhoang120904)

Feel free to reach out for questions, suggestions, or collaboration opportunities!

## 📄 License

This project is developed for educational and portfolio purposes. All rights reserved.

---

### 🙏 Acknowledgments

- **Nguyen Quang Huy** - Frontend development assistance
- **Spring Boot Community** - Comprehensive framework documentation
- **React Community** - Modern frontend development patterns
- **YouTube Educators** - Invaluable learning resources

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ by [Huy Hoang](https://github.com/Huyhoang120904)

</div>
