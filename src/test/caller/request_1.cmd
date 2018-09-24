set URL=http://mbasweb20.oso.mmbank.ru:9445/ws-eetp

curl -X POST %URL%/outcoming ^
   --header "content-type: text/xml; charset utf-8" ^
   -f "remote_url=aHR0cDovL3dlYm1iYW5rLWRldjIwLm9zby5tbWJhbmsucnU6OTQ0NS93cy1lZXRwL2R1bW154oCL"
   -d @request_1.xml > request_1-result.txt
