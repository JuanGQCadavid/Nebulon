# Use an official Python runtime as a parent image
FROM python:3.7.0-stretch

# Set the working directory used by the image
WORKDIR /server

# Copies all that is in this directory into /server
COPY . /server

# Needed libraries
RUN pip install --trusted-host pypi.python.org -r requirements.txt

# Make port 8000 available to the world outside this container
EXPOSE 8000

# Run this when the container launches
CMD ["python", "hello_world.py"]