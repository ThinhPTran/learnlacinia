(ns try-lacinia.schema
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [com.walmartlabs.lacinia.util :refer [attach-resolvers]]
            [com.walmartlabs.lacinia.schema :as schema]
            [try-lacinia.db :as db]))

(def star-wars-schema
  (-> (io/resource "star-wars-schema.edn")
      slurp
      edn/read-string
      (attach-resolvers {:resolve-hero           db/resolve-hero
                         :resolve-human          db/resolve-human
                         :resolve-humans         db/resolve-humans
                         :resolve-droid          db/resolve-droid
                         :resolve-droids         db/resolve-droids
                         :resolve-friends        db/resolve-friends
                         :resolve-mutate-human   db/resolve-mutate-human})
      schema/compile))
