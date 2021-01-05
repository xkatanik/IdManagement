ID management system, Kristian Katanik

Git repository is available at https://github.com/xkatanik/IdManagement.git

Core of the ID management system was reused from Data warehouse of project "ANALYZA". To get more information about project "ANALYZA" or to compare source code of ID management to project "ANALYZA", I recommend to check bachelor thesis from Bc. David Brilla. Source code of project is private, so the only way is to use IS of FI MUNI and search thesis from Bc. David Brilla and check appendix which contains source code.


<<How to run system?>>

To run ID management system prototype, run script DeployIdManagement.sh (using command ./DeployIdManagement.sh) in the same folder as files data-warehouse-monolith and PostgreSQL.sh (tested on Ubuntu 19). Script prepares PostgresSQL database, JDK and maven to launch prototype.

After successful running of ID management prototype, I recommend to use Postman software to use REST API. REST API is available on address http://localhost:8080/generic-objects/ or http://localhost:8080/links/ or http://localhost:8080/query-builder/.

RCTX-DWP API is imlemented via fastAPI https://fastapi.tiangolo.com/. To run it, follow the guide on website and run with "uvicorn rctx-dwp:app --reload" in folder rctx-dwp

DWP_REST_API_Manual.pdf contains description of available DWP REST calls and its attributes.
RCTX-DWP_REST_API_Manual.pdf contains description of available RCTX-DWP REST calls and its attributes.

consumer folder contains two consumers for getting messages from Apache Kafka message queue for storing probands and specimn.
producers folder contains producers and applications for getting identifiers and preparing messages to send to Apacha Kafka.

Apache Kafka can be easily deployed and run with guide at https://kafka.apache.org/quickstart.

In order to create test environment it is necessary to change adresses in consumers, producers and rctx-dwp to adresses where are running Apache Kafka and RCTX-DWP and DWP API.

