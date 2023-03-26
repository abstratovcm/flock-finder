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
│   │   │   ├── CattleWeight.java
│   │   │   ├── CattleWeightServlet.java
│   │   │   └── DatabaseManager.java
│   │   └── webapp
│   │       ├── dashboard.jsp
│   │       ├── homepage.jsp
│   │       ├── css
│   │       │   └── styles.css
│   │       ├── lib
│   │       │   ├── jstl-1.2.jar
│   │       │   └── sqlite-jdbc-3.41.0.1.jar
│   │       └── WEB-INF
│   │           ├── web.xml
│   │           └── data
│   │               └── weights.db
```

./deploy.sh
chmod +x deploy.sh

site: https://flockfinder.herokuapp.com/
