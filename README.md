## Project structure

```
├── README.md
├── Procfile
├── LICENSE
├── deploy.sh
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── Admin.java
│   │   │   ├── CattleWeight.java
│   │   │   ├── CattleWeightServlet.java
│   │   │   ├── ContextInitializerServlet.java
│   │   │   ├── DatabaseManager.java
│   │   │   ├── LimitedUser.java
│   │   │   ├── LoginServlet.java
│   │   │   ├── RegisterServlet.java
│   │   │   ├── User.java
│   │   │   └── UserRepository.java
│   │   ├── resources
│   │   │   └── hibernate.cfg.xml
│   │   └── webapp
│   │       ├── dashboard.jsp
│   │       ├── homepage.jsp
│   │       ├── css
│   │       │   └── styles.css
│   │       └── WEB-INF
│   │           ├── web.xml
│   │           └── data
│   │               ├── userList.db
│   │               └── weights.db
```

./deploy.sh

chmod +x deploy.sh

site: https://flockfinder.herokuapp.com/
