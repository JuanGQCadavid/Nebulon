from flaskext.mysql import MySQL
from flask import g

mysql = None
connection = None

# Called when handling requests
def get_db():
    if 'db' not in g:
        g.db = mysql.connect()

    return g.db

# Automatically called when the request is ended
def close_db():
    db = g.pop('db', None)

    if db is not None:
        db.close()

# Called in the app factory
def init_app(app):
    app.teardown_appcontext(close_db)
    mysql = MySQL()
    mysql.init_app(app)
