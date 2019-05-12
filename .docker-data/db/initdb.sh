#!/bin/bash

set -e

psql -U postgres << EOF
CREATE DATABASE steganomed OWNER postgres;
EOF
