import pymysql
from flask import g, current_app

# Called when handling requests
def get_db():
    
    if 'db' not in g:
        connection = pymysql.connect(host='localhost',
                                     user='nebulon',
                                     password='nebulon',
                                     db='nebulon',
                                     cursorclass=pymysql.cursors.DictCursor
        )
        
        g.db = connection

    return g.db

# Automatically called when the request is ended
def close_db(e = None):
    db = g.pop('db', None)

    if db is not None:
        db.close()

# Called in the app factory
def init_app(app):
    app.teardown_appcontext(close_db)
