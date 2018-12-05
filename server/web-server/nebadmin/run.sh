#!/bin/sh

# Variables needed for flask
export FLASK_APP=nebadmin
export FLASK_ENV=development

flask run -h '0.0.0.0'
