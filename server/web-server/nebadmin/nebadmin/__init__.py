import os

from flask import Flask


def create_app(test_config = None):

    # Create and configure the app
    app = Flask(__name__, instance_relative_config = True)
    # Default configuration
    app.config.from_mapping(
        # Used for flask to secure things
        SECRET_KEY = 'dev',
        # Database stuff
        MYSQL_DATABASE_USER = 'nebulon',
        MYSQL_DATABASE_PASSWORD = 'nebulon',
        MYSQL_DATABASE_DB = 'nebulon',
        MYSQL_DATABASE_HOST = 'localhost'
    )

    if test_config is None:
        # Overwrite the default config
        # Load the instance config, if it exists, when not testing
        app.config.from_pyfile('config.py', silent = True)
    else:
        app.config.from_mapping(test_config)

    # Create instance path
    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    # Database initialization
    from . import database
    database.init_app(app)

    # Blueprints
    # 1. Authentication and user (installators) creation
    # from . import

    # 2. Management (monitoring, inventory, contracts)
    from . import management
    app.register_blueprint(management.blueprint)
    
    return app
    
