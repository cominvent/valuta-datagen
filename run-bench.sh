#!/usr/bin/env bash
esrally --pipeline=benchmark-only --target-hosts=17.0.0.1:9200 --track-repository=valutareg-rally \
  --track=valutareg --challenge=query