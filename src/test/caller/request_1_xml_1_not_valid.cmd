rem set URL=http://localhost:8080/gs-rest-service-0.1.0
rem set URL=http://localhost:8080
set URL=http://mbasweb12.oso.mmbank.ru:8457/ws-eetp
rem set URL=localhost:8457/ws-eetp

curl -X POST %URL%/incoming ^
   --header "content-type: text/xml; charset utf-8" ^
   -d @request_1_xml1_not_valid.xml > request_1_xml_1_not_valid-result.txt

