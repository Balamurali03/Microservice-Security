# Enterprise Product Management & OTP Authentication Platform

## Overview

A production-grade microservices platform built using Spring Boot 3.5.15 and Java 21.

The platform provides:

* Passwordless Authentication
* Email OTP Login (Gmail SMTP)
* Mobile OTP Login (Twilio SMS)
* JWT Authentication
* Redis OTP Storage with TTL
* API Gateway Security
* Eureka Service Discovery
* User Profile Management
* Product Management
* Excel Bulk Upload
* Spring Batch Processing
* Elasticsearch Search
* Weekly Admin Reports
* Scheduled Email Notifications

---

# System Architecture

```text

                                    ┌─────────────────────┐
                                    │      CLIENT         │
                                    └──────────┬──────────┘
                                               │
                                               ▼
                                ┌───────────────────────────┐
                                │       API GATEWAY         │
                                │         :8080             │
                                └──────────┬────────────────┘
                                           │
                     ┌─────────────────────┼─────────────────────┐
                     │                     │                     │
                     ▼                     ▼                     ▼

       ┌──────────────────┐   ┌──────────────────┐   ┌──────────────────┐
       │   AUTH SERVICE   │   │   USER SERVICE   │   │   DATA SERVICE   │
       │      :8081       │   │      :8082       │   │      :8083       │
       └────────┬─────────┘   └────────┬─────────┘   └────────┬─────────┘
                │                      │                      │
                ▼                      ▼                      ▼

        ┌─────────────┐         ┌─────────────┐       ┌─────────────┐
        │    Redis    │         │    MySQL    │       │    MySQL    │
        │ OTP Storage │         │ User Data   │       │ Product Data│
        └─────────────┘         └─────────────┘       └──────┬──────┘
                                                             │
                                                             ▼

                                                   ┌────────────────┐
                                                   │ Elasticsearch  │
                                                   │ Product Search │
                                                   └────────────────┘


                ┌────────────────────┐
                │ Gmail SMTP/Twilio  │
                └─────────┬──────────┘
                          │
                          ▼

                  Notifications

All services are registered with Eureka Discovery Server (:8761)

```

---

# Services

## Eureka Discovery Server

### Responsibilities

* Service Registration
* Service Discovery
* Health Monitoring

### Port

```properties
8761
```

---

## API Gateway

### Responsibilities

* Single Entry Point
* Route Management
* JWT Validation
* Authorization
* Header Propagation

### Port

```properties
8080
```

### Routes

```text
/auth/**      -> AUTH-SERVICE
/users/**     -> USER-SERVICE
/products/**  -> DATA-SERVICE
/reports/**   -> DATA-SERVICE
```

### JWT Flow

```text
Client
  |
JWT Token
  |
API Gateway
  |
Validate JWT
  |
Extract Claims
  |
Add Headers

X-USER-EMAIL
X-USER-ROLE

  |
Forward Request
```

---

## Auth Service

### Responsibilities

* Generate OTP
* Store OTP in Redis
* Send Email OTP
* Send Mobile OTP
* Verify OTP
* Generate JWT
* Check Existing User

### Port

```properties
8081
```

---

### Authentication Flow

#### Existing User

```text
Email/Mobile
      |
Request OTP
      |
Redis Store OTP
      |
Send OTP
      |
Verify OTP
      |
Call USER-SERVICE
      |
User Exists ?
      |
YES
      |
Generate JWT
      |
Dashboard
```

#### New User

```text
Email/Mobile
      |
Request OTP
      |
Verify OTP
      |
Call USER-SERVICE
      |
User Exists ?
      |
NO
      |
Create Profile
      |
Login Again
```

---

### OTP Channels

#### Email OTP

```text
Gmail SMTP
```

#### Mobile OTP

```text
Twilio SMS
```

---

### Redis OTP Storage

```text
TTL = 60 Seconds
```

Example Keys:

```text
OTP:EMAIL:user@gmail.com

OTP:MOBILE:+919876543210
```

---

### Factory Design Pattern

```text

                 NotificationService
                           |
        ------------------------------------
        |                                  |
        ▼                                  ▼

 EmailNotificationService      SmsNotificationService
       (SMTP)                        (Twilio)

```

Future Extensions:

* WhatsApp OTP
* Push Notifications
* Voice OTP

---

## User Service

### Responsibilities

* Create Profile
* Update Profile
* Get User By Email
* Get User By Mobile
* Manage User Roles
* Admin Retrieval

### Port

```properties
8082
```

---

### Roles

```java
ADMIN
USER
```

Stored as:

```java
@Enumerated(EnumType.STRING)
private Role role;
```

---

## Data Service

### Responsibilities

* Product CRUD
* Bulk Upload
* Excel Processing
* Search Products
* Weekly Reports
* Scheduler Jobs

### Port

```properties
8083
```

---

# Product Management Module

### Admin Features

```text
Create Product
Update Product
Delete Product
Bulk Upload Products
View Products
```

### User Features

```text
View Products
Search Products
```

---

# Excel Bulk Upload Module

### Technology

* Spring Batch
* Apache POI

### Flow

```text

Excel File
     |
Upload API
     |
Job Launcher
     |
Spring Batch Job
     |
Reader
     |
Processor
     |
Writer
     |
MySQL
     |
Elasticsearch

```

### Chunk Processing

```text
Chunk Size = 100
```

Benefits:

* Handles Large Files
* Better Memory Usage
* Faster Processing

---

# Elasticsearch Search Module

### Features

```text
Keyword Search
Category Search
Pagination
Sorting
Fuzzy Search
```

### Flow

```text

Client
   |
Search Request
   |
DATA-SERVICE
   |
Elasticsearch
   |
Results

```

---

# Weekly Reporting Module

### Schedule

```text
Every Monday - 09:00 AM

Every Friday - 09:00 AM
```

### Process

```text

Scheduler
    |
Fetch Product Statistics
    |
Get Admin Users
    |
Generate Report
    |
Send Email
    |
All Admins

```

### Report Includes

```text
Total Product Count
Category Wise Count
Low Stock Products
```

---

# Authentication APIs

## Request OTP

```http
POST /auth/request-otp
```

Email

```json
{
  "otpType":"EMAIL",
  "destination":"user@gmail.com"
}
```

Mobile

```json
{
  "otpType":"MOBILE",
  "destination":"+919876543210"
}
```

---

## Verify OTP

```http
POST /auth/verify-otp
```

```json
{
  "otpType":"EMAIL",
  "destination":"user@gmail.com",
  "otp":"123456"
}
```

---

# User APIs

## Create Profile

```http
POST /users/profile/create
```

## Update Profile

```http
PUT /users/profile/{id}
```

## Get User By Email

```http
GET /users/email/{email}
```

---

# Product APIs

## Create Product

```http
POST /products
```

## Update Product

```http
PUT /products/{id}
```

## Delete Product

```http
DELETE /products/{id}
```

## Get Product

```http
GET /products/{id}
```

## Search Product

```http
GET /products/search?keyword=laptop
```

## Upload Excel

```http
POST /products/upload
```

Multipart Form Data:

```text
file=products.xlsx
```

---

# Databases

## User Database

```text
MySQL
```

Stores:

```text
Users
Profiles
Roles
```

---

## Product Database

```text
MySQL
```

Stores:

```text
Products
Inventory
Product Metadata
```

---

## Search Engine

```text
Elasticsearch
```

Stores:

```text
Indexed Product Documents
```

---

# External Integrations

## Redis

```text
OTP Storage
```

## Twilio

```text
SMS OTP
```

## Gmail SMTP

```text
Email OTP
Weekly Reports
Notifications
```

---

# Running Infrastructure

## Redis

```bash
docker run -d --name redis-server -p 6379:6379 redis:latest
```

## MySQL

```text
localhost:3306
```

## Elasticsearch

```text
localhost:9200
```

---

# Startup Order

```text
1. Eureka Server

2. Redis

3. MySQL

4. Elasticsearch

5. API Gateway

6. Auth Service

7. User Service

8. Data Service
```

---

# Design Patterns Used

* Factory Pattern
* Builder Pattern
* Dependency Injection
* Gateway Pattern
* Service Discovery Pattern
* Repository Pattern
* Strategy Ready Architecture

---

# Key Concepts Demonstrated

* Microservices Architecture
* JWT Authentication
* Redis Caching
* OTP Authentication
* Spring Batch
* Elasticsearch
* API Gateway
* Eureka Discovery
* Feign Clients
* Scheduler Jobs
* Docker Integration
* Distributed Systems

---

# Future Enhancements

## Phase 6

* Kafka Integration
* Audit Logging
* Dead Letter Queue
* Retry Mechanism
* Distributed Tracing
* Zipkin
* Micrometer

## Phase 7

* Docker Compose
* Kubernetes
* Jenkins CI/CD
* Prometheus
* Grafana
* Centralized Logging

---

# Author

**Balamurali R**

Java Backend Developer

Built With

* Java 21
* Spring Boot 3.5.15
* Spring Cloud
* MySQL
* Redis
* Elasticsearch
* Spring Batch
* JWT
* Twilio
* Gmail SMTP
* Docker
* Eureka Discovery
* API Gateway

```
```
