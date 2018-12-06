# Management blueprint

from flask import (
    Blueprint, flash, g, redirect, render_template, request, url_for
)

# Database connection function
from nebadmin.database import get_db

blueprint = Blueprint('management', __name__, url_prefix='/management')

# Index view
@blueprint.route('/')
def index():
    # Get information from database and pass it as context
    # - Initialize the connection and cursor
    db = get_db()
    cursor = db.cursor()

    # - Execute query
    cursor.execute('SELECT c.company_name, '\
                   'n.nebulon_fg1name, n.nebulon_fg1level, '\
                   'n.nebulon_fg2name, n.nebulon_fg2level, '\
                   'l.loan_starting_date, l.loan_ending_date '\
                   'FROM nebulon AS n '\
                   'INNER JOIN loan AS l ON n.loan_id = l.loan_id '\
                   'INNER JOIN company AS c ON c.company_id = l.company_id '\
                   'ORDER BY n.nebulon_id')
    nebulizers = cursor.fetchall()
    
    return render_template('management/index.html', nebulizers = nebulizers)

@blueprint.route('/update')
def update_liquid_level():
    
    db = get_db()
    cursor = db.cursor()

    cursor.execute('SELECT n.nebulon_fg1name, n.nebulon_fg1level, '\
                   'n.nebulon_fg2name, n.nebulon_fg2level '\
                   'FROM nebulon AS n ORDER BY nebulon_id')
    levels = cursor.fetchall()

    return str(levels)
