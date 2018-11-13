from django.db import models

# Create your models here.
# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey has `on_delete` set to the desired behavior.
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models

class Company(models.Model):
    company_id = models.IntegerField(primary_key=True)
    company_name = models.CharField(max_length=45)
    company_address = models.CharField(max_length=45)
    company_state = models.CharField(max_length=45, blank=True, null=True)

    class Meta:
        # managed = False
        db_table = 'company'


class CompanyContact(models.Model):
    contact_id = models.IntegerField(primary_key=True)
    contact_name = models.CharField(max_length=45)
    contact_phone = models.CharField(max_length=45)
    contact_extra_info = models.CharField(max_length=45, blank=True, null=True)
    contact_roll = models.CharField(max_length=45, blank=True, null=True)
    company = models.ForeignKey(Company, models.DO_NOTHING)

    class Meta:
        # managed = False
        db_table = 'company_contact'


class Loan(models.Model):
    loan_id = models.IntegerField(primary_key=True)
    company = models.ForeignKey(Company, models.DO_NOTHING)
    loan_starting_date = models.DateTimeField()
    loan_ending_date = models.DateTimeField()
    loan_state = models.FloatField()
    loan_description = models.CharField(max_length=45, blank=True, null=True)

    class Meta:
        # managed = False
        db_table = 'loan'

class Payment(models.Model):
    payment_id = models.IntegerField(primary_key=True)
    loan = models.ForeignKey(Loan, models.DO_NOTHING)
    payment_date = models.DateTimeField()
    payment_amount = models.FloatField()
    payment_description = models.CharField(max_length=45, blank=True, null=True)
    payment_photo = models.CharField(max_length=45, blank=True, null=True)
    
    class Meta:
        # managed = False
        db_table = 'payment'


class NebulonSpec(models.Model):
    spec_id = models.IntegerField(primary_key=True)
    spec_vendor = models.CharField(max_length=45)
    spec_engine_code = models.CharField(max_length=45)
    spec_size = models.CharField(max_length=45, blank=True, null=True)

    class Meta:
        # managed = False
        db_table = 'nebulon_spec'


class Nebulon(models.Model):
    nebulon_id = models.IntegerField(primary_key=True)
    nebulon_state = models.CharField(max_length=45)
    nebulon_liquid_level = models.IntegerField()
    nebulon_schedule = models.TextField(blank=True, null=True)
    nebulon_private_ip = models.CharField(max_length=40, blank=True, null=True)
    nebulon_purchase_date = models.DateTimeField()
    spec = models.ForeignKey('NebulonSpec', models.DO_NOTHING)
    loan = models.ForeignKey(Loan, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        # managed = False
        db_table = 'nebulon'
