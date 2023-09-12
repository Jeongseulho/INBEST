import pkg_resources

__version__ = pkg_resources.require("eureka-client")[0].version

from .client import EurekaClient

__all__ = ["EurekaClient"]
