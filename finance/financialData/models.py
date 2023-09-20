from django.db import models

class Company(models.Model):
    seq = models.AutoField(primary_key=True)
    company_code = models.CharField(max_length=10)
    company_name = models.CharField(max_length=255)
    company_industry_code = models.IntegerField(default=None, null=True)
    company_stock_code = models.CharField(max_length=10)
    company_real_industry_code = models.CharField(max_length=10, null=True)

class CompanyIndustry(models.Model):
    company_industry_code = models.IntegerField(primary_key=True)
    industry_name = models.CharField(max_length=255)
