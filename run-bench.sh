#!/usr/bin/env bash
esrally --pipeline=benchmark-only --target-hosts=127.0.0.1:9200 --cluster-health=yellow \
  --track-repository=valutareg-rally --track=valutareg --challenge=query
