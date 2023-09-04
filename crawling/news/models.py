from django.db import models
from django.conf import settings


class News(models.Model):
    title = models.CharField(max_length=200)
    content = models.TextField()
    image = models.URLField(max_length=400, blank=True)
    url = models.URLField(max_length=400)
    date = models.DateTimeField()