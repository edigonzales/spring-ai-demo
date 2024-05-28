# spring-ai-demo

https://www.urlencoder.org/de/

## Simple

```
curl -i -X GET http://localhost:8080/api/simple/jokes
```

```
curl -i -X GET http://localhost:8080/api/simple/jokes?message=erz%C3%A4hle+mir+einen+deutschen+witz
```

## Prompts

https://platform.openai.com/docs/guides/prompt-engineering
https://www.deeplearning.ai/short-courses/chatgpt-prompt-engineering-for-developers/

```
curl -i -X GET http://localhost:8080/api/prompts/jokes
```

```
curl -i -X GET http://localhost:8080/api/prompts/youtubers
```

```
curl -i -X GET http://localhost:8080/api/prompts/youtubers?genre=java+language
```

```
curl -i -X GET http://localhost:8080/api/prompts/dad-jokes
```

## Output parsers

```
curl -i -X GET http://localhost:8080/api/output/songs
```

```
curl -i -X GET http://localhost:8080/api/output/books/author/george+orwell
```

```
curl -i -X GET http://localhost:8080/api/output/books/author/martin%20sutter
```

```
curl -i -X GET http://localhost:8080/api/output/books/author/craig%20walls
```

```
curl -i -X GET http://localhost:8080/api/output/books/by-author?george+orwell
```

## Stuff the prompt

```
curl -i -X GET http://localhost:8080/api/output/stuff/organisation
```

```
curl -i -X GET http://localhost:8080/api/output/stuff/organisation?stuffit=true
```

```
curl -i -X GET http://localhost:8080/api/output/stuff/organisation?stuffit=true&message=Wer+ist+Amtschef+oder+Amtschefin+im+Amt+f%C3%BCr+Raumplanung+des+Kantons+Solothurn+und+zu+welchem+Departement+geh%C3%BCrt+das+Amt
```

## RAG

```
curl -i -X GET http://localhost:8080/api/rag/organisation
```

```
curl -i -X GET http://localhost:8080/api/rag/organisation?message=Wie%20viele%20Departement%20gibt%20es%20im%20Kanton%20Solothurn
```

```
curl -i -X GET http://localhost:8080/api/rag/organisation?message=Wie%20viele%20Amtschefs%20gibt%20es%20im%20Kanton%20Solothurn
```


```
curl -i -X GET http://localhost:8080/api/rag/organisation?message=Wie%20viele%20weibliche%20Amtschefs%20gibt%20es%20im%20Kanton%20Solothurn
```