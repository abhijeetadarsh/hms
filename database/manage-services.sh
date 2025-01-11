#!/bin/bash

# Function to display usage
show_usage() {
  echo "Usage: $0 [OPTION]"
  echo "Options:"
  echo "  --start db    Start MySQL database"
  echo "  --start sc    Start SchemaCrawler"
  echo "  --stop db     Stop MySQL database"
  echo "  --stop sc     Stop SchemaCrawler"
  echo "  --stop all    Stop both services"
  echo "  --help        Display this help message"
}

# Function to start docker if not running
ensure_docker_running() {
  if ! systemctl is-active --quiet docker; then
    echo "Starting Docker service..."
    sudo systemctl start docker
  fi
}

# Function to start MySQL
start_mysql() {
  echo "Starting MySQL..."
  ensure_docker_running
  sudo docker-compose -f ./docker/mysql.yml up -d
}

# Function to start SchemaCrawler
start_schemacrawler() {
  echo "Starting SchemaCrawler..."
  ensure_docker_running
  sudo docker-compose -f ./docker/schemacrawler.yml up
}

# Function to stop MySQL
stop_mysql() {
  echo "Stopping MySQL..."
  sudo docker-compose -f ./docker/mysql.yml down
}

# Function to stop SchemaCrawler
stop_schemacrawler() {
  echo "Stopping SchemaCrawler..."
  sudo docker-compose -f ./docker/schemacrawler.yml down
}

# Main script logic
case "$1" in
--start)
  case "$2" in
  db)
    start_mysql
    ;;
  sc)
    start_schemacrawler
    ;;
  *)
    echo "Error: Invalid start option"
    show_usage
    exit 1
    ;;
  esac
  ;;
--stop)
  case "$2" in
  db)
    stop_mysql
    ;;
  sc)
    stop_schemacrawler
    ;;
  all)
    stop_schemacrawler
    stop_mysql
    ;;
  *)
    echo "Error: Invalid stop option"
    show_usage
    exit 1
    ;;
  esac
  ;;
--help)
  show_usage
  ;;
*)
  echo "Error: Invalid option"
  show_usage
  exit 1
  ;;
esac
