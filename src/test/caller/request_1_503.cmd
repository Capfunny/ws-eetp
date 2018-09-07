rem set URL=http://localhost:8080/gs-rest-service-0.1.0
set URL=http://localhost:8457/ws-eetp
rem set URL=http://localhost:8080/ws-eetp

curl -X POST %URL%/xmlreceiver_505 ^
   --header "content-type: text/xml; charset utf-8" ^
   -d @request_1.xml
