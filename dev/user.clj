(ns user
  "Custom repl customization for local development."
  (:require
    [byte-streams :as bytes :refer [bytes=]]
    (blocks
      [core :as block]
      [data :as data]
      [store :as store])
    (blocks.store
      [buffer :refer [buffer-block-store]]
      [cache :refer [caching-block-store]]
      [file :refer [file-block-store]]
      [memory :refer [memory-block-store]]
      [replica :refer [replica-block-store]]
      [tests :as tests])
    [clojure.java.io :as io]
    [clojure.repl :refer :all]
    [clojure.string :as str]
    [clojure.test.check :as check]
    [clojure.test.check.generators :as gen]
    [clojure.test.check.properties :as prop]
    [multihash.core :as multihash]
    [multihash.digest :as digest])
  (:import
    blocks.data.Block
    multihash.core.Multihash))


; Conditional imports from clj-stacktrace and clojure.tools.namespace:
(try (require '[clojure.stacktrace :refer [print-cause-trace]]) (catch Exception e nil))
(try (require '[clojure.tools.namespace.repl :refer [refresh]]) (catch Exception e nil))


(def test-blocks
  (tests/generate-blocks! 10 1024))


(def tbs
  "Temporary block store in target."
  (file-block-store "target/blocks"))
