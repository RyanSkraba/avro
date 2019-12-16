#!/bin/bash

# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

set -x

case "$TRAVIS_OS_NAME" in
"linux")

    # The digest of the ubertool container from the cache, if any.
    CACHED_CI_UBERTOOL_IMAGE=$(docker images -q ubertool)

    # Rebuild the ubertool container using the build cache.
    tar -cf- lang/ruby/Gemfile share/docker/Dockerfile | docker build -t ubertool -f share/docker/Dockerfile -
    CI_UBERTOOL_IMAGE=$(docker images -q ubertool)

    # If and only if the ubertool has changed, save it back into the cache.
    [[ "z$CI_UBERTOOL_IMAGE" != "z$CACHED_CI_UBERTOOL_IMAGE" ]] \
        && docker save -o $HOME/docker_images/ubertool.tar $(docker history -q ubertool | grep -v missing) ubertool \
        || true
    ;;
*)
    echo "Invalid PLATFORM"
    exit 1
    ;;
esac
