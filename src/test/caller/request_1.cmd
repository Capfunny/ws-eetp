rem set URL=http://localhost:8080/gs-rest-service-0.1.0
set URL=http://localhost:8080
rem set URL=http://mbasweb13.oso.mmbank.ru:5581/ws-eetp
rem set URL=localhost:8457/ws-eetp

curl -X POST %URL%/incoming ^
   --header "content-type: text/xml; charset utf-8" ^
   -d @request_1.xml > request_1-result.txt

