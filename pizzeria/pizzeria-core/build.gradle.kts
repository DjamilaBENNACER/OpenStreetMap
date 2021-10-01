description = "Core module"

dependencies {
  api("org.springframework:spring-jdbc")
  api("org.springframework:spring-context")
  api("org.springframework:spring-orm")
  api("org.springframework.data:spring-data-jpa")

  api("com.jolbox:bonecp:${property("version.bonecp")}")
  api("xml-apis:xml-apis:${property("version.xml-apis")}")
  api("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:${property("version.hibernate-jpa")}")
  api("org.slf4j:jcl-over-slf4j:${property("version.slf4j")}")
  api("ch.qos.logback:logback-classic:${property("version.logback")}")
  api("mysql:mysql-connector-java:${property("version.mysql")}")
  api("org.hibernate:hibernate-entitymanager:${property("version.hibernate")}")
  api("javax.xml.bind:jaxb-api:${property("version.jaxb")}")
}

