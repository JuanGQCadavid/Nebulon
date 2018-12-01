# Management blueprint

from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for
)

blueprint = Blueprint('management', __name__, url_prefix='/management')

# Index view
@blueprint.route('/')
def index():

    # Get infor from database and pass it as context
    return render_template('management/index.html')
    
