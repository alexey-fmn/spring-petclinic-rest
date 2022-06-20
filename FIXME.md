curl -X GET "http://localhost:8080/owners/12345" -H "accept: */*"
Возвращается неправильный статус-код
ОР: 404
ФР: 200

curl -X POST "http://localhost:8080/owners/3/pets/4/visits" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"date\": \"2022-06-20\", \"description\": \"blahblah\", \"id\": 99}"
ОР: 200
ФР: Возвращает всегда 500
