set URL=http://webmbank-dev12.oso.mmbank.ru:8457/ws-eetp
rem set URL=http://localhost:8457/ws-eetp
curl -X POST %URL%/incoming ^
   --header "content-type: text/xml; charset utf-8" ^
   -d @request_1.xml > request_1-result.txt

