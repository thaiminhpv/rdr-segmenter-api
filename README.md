# A lightweight Spring Boot RestAPI for [RDRsegmenter](https://github.com/datquocnguyen/RDRsegmenter)

```bash
./gradlew bootBuildImage
docker compose build
docker compose up
```

Then you can access the API at http://localhost:8080

API usage can be found in [the restapi.http file](./restapi.http).

```bash
> curl "http://localhost:8080/word_segment?text=hello%20world"
{"tokens":["hello","world"]}

> curl -X POST "http://localhost:8080/batch_word_segment" \
    -H "Content-Type: application/json" \
    -d '{"texts": ["Ông Nguyễn Khắc Chúc  đang làm việc tại Đại học Quốc gia Hà Nội. Bà Lan, vợ ông Chúc, cũng làm việc tại đây.", "hello world"]}' 
{"tokens":[["Ông","Nguyễn_Khắc_Chúc","đang","làm_việc","tại","Đại_học","Quốc_gia","Hà_Nội",".","Bà","Lan",",","vợ","ông","Chúc",",","cũng","làm_việc","tại","đây","."],["hello","world"]]}
```

