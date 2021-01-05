
# install postgresql
sudo apt update
sudo apt install postgresql postgresql-contrib


# change password of postgres user
sudo -u postgres psql -c "alter user postgres password 'postgres';"

# create database
sudo -u postgres psql -c "create database test_database;"

# set privileges
sudo -u postgres psql -c "grant all privileges on database test_database to postgres;"


# start postgres server
sudo service postgresql start


# check to see running processes with postgres
# ps aux | grep postgres

# url should be localhost:5432/test_database with login postgres and password postgres



