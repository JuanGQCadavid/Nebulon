from flask import Flask
from flask_restful import Api, Resource, reqparse

app = Flask(__name__)
api = Api(app)

users = [
    {
        'name': 'Nicolas',
        'age':  42,
        'ocupattion': 'Network Engineer'
    },
    {
        'name': 'Elvin',
        'age':  32,
        'ocupattion': 'Doctor'

    },
    {
        'name': 'Jass',
        'age':  22,
        'ocupattion': 'Web  developer'

    }
]

class User(Resource):
    def get(self, name):
        for user in users:
            if(name == user['name']):
                return user,200
        return 'User not found', 404
        pass

    def post(self, name):
        parser = reqparse.RequestParser()
        parser.add_argument('age')
        parser.add_argument('ocupattion')
        args = parser.parse_args()

        for user in users :
            if(name == user['name']):
                user['age'] = args['age']
                user['ocupattion'] = args['ocupattion']
                return user,200

        user = {
            'name': name,
            'age': args['age'],
            'ocupattion': args['ocupattion']
        }

        users.append(user)

        return user,201


    def put(self, name):
        parser = reqparse.RequestParser()
        parser.add_argument('age')
        parser.add_argument('ocupattion')
        args = parser.parse_args()

        for user in users :
            if(name == user['name']):
                return 'User with the name {} already exist'.format(name), 400 # Bad RequestParser

        user = {
            'name': name,
            'age': args['age'],
            'ocupattion': args['ocupattion']
        }

        users.append(user)

        return user,201 #Create

    def delete(self, name):
        global users
        users = [user for user in users if user['name'] != name]
        return "{} is delete.".format(name), 200 # JSON, ok
        pass


api.add_resource(User, "/user/<string:name>")
app.run(host='0.0.0.0',debug = True)
