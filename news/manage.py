#!/usr/bin/env python
"""Django's command-line utility for administrative tasks."""
import os
import sys

import py_eureka_client.eureka_client as eureka_client
from django.conf import settings

# Eureka client 등록
# EUREKA_SERVER = 'http://192.168.100.148:8761/eureka/'

# def register_with_eureka():
#     # EUREKA_CLIENT 설정 및 서버에 등록.
#     eureka_client.init(
#         eureka_server=EUREKA_SERVER,
#         app_name="news-service",
#         instance_port=8000,  # Django 애플리케이션의 포트
#     )

# register_with_eureka()

def main():
    """Run administrative tasks."""
    os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'crawling.settings')
    try:
        from django.core.management import execute_from_command_line
    except ImportError as exc:
        raise ImportError(
            "Couldn't import Django. Are you sure it's installed and "
            "available on your PYTHONPATH environment variable? Did you "
            "forget to activate a virtual environment?"
        ) from exc
    execute_from_command_line(sys.argv)


if __name__ == '__main__':
    main()
