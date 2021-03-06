(ns try-lacinia.db
  (:require
    [com.walmartlabs.lacinia.schema :as schema]))

(def ^:private humans-data
  (atom   [{:id          "1000"
            :name        "Luke Skywalker"
            :friends     ["1002", "1003", "2000", "2001"]
            :appears-in  ["NEWHOPE" "EMPIRE" "JEDI"]
            :home-planet "Tatooine"
            :force-side  "3001"}
           {:id          "1001"
            :name        "Darth Vader"
            :friends     ["1004"]
            :appears-in  ["NEWHOPE" "EMPIRE" "JEDI"]
            :home-planet "Tatooine"
            :force-side  "3000"}
           {:id          "1003"
            :name        "Leia Organa"
            :friends     ["1000", "1002", "2000", "2001"]
            :appears-in  ["NEWHOPE" "EMPIRE" "JEDI"]
            :home-planet "Alderaan"
            :force-side  "3001"}
           {:id         "1002"
            :name       "Han Solo"
            :friends    ["1000", "1003", "2001"]
            :appears-in ["NEWHOPE" "EMPIRE" "JEDI"]
            :force-side "3001"}
           {:id         "1004"
            :name       "Wilhuff Tarkin"
            :friends    ["1001"]
            :appears-in ["NEWHOPE"]
            :force-side "3000"}]))

(def ^:private droids-data
  (atom   [{:id               "2001"
            :name             "R2-D2"
            :friends          ["1000", "1002", "1003"]
            :appears-in       ["NEWHOPE" "EMPIRE" "JEDI"]
            :primary-function "ASTROMECH"}
           {:id               "2000"
            :name             "C-3PO"
            :friends          ["1000", "1002", "1003", "2001"]
            :appears-in       ["NEWHOPE" "EMPIRE" "JEDI"]
            :primary-function "PROTOCOL"}]))

(def ^:private hero-data
  {"NEWHOPE" "2001"
   "EMPIRE"  "1002"
   "JEDI"    "1000"})

(defn ^:private first-match [data key value]
  (-> (filter #(= (get % key) value) data)
      first))

(defn resolve-hero
  [ctx args value]
  (let [episode (:episode args "NEWHOPE")
        hero-id (get hero-data episode)
        tag-humans-data (map #(schema/tag-with-type % :human) @humans-data)
        tag-droids-data (map #(schema/tag-with-type % :droid) @droids-data)
        character-data (concat tag-humans-data tag-droids-data)]
    (first-match character-data :id hero-id)))

(defn resolve-droid
  [ctx args value]
  (let [tag-droids-data (map #(schema/tag-with-type % :droid) @droids-data)]
    (first-match tag-droids-data :id (:id args))))

(defn resolve-friends
  [ctx args value]
  (let [tag-humans-data (map #(schema/tag-with-type % :human) @humans-data)
        tag-droids-data (map #(schema/tag-with-type % :droid) @droids-data)
        character-data (concat tag-humans-data tag-droids-data)]
    (map #(first-match character-data :id %) (:friends value))))

(defn resolve-human
  [ctx args value]
  (let [tag-humans-data (map #(schema/tag-with-type % :human) @humans-data)]
    (first-match tag-humans-data :id (:id args))))

(defn resolve-humans
  [ctx args value]
  (let [tag-humans-data (map #(schema/tag-with-type % :human) @humans-data)]
    tag-humans-data))

(defn resolve-droids
  [ctx args value]
  (let [tag-droids-data (map #(schema/tag-with-type % :droid) @droids-data)]
    tag-droids-data))

(defn resolve-mutate-human
  [ctx args value]
  (let [id (:id args)
        name (:name args)
        tmp (swap! humans-data conj {:id id :name name})
        tag-humans-data (map #(schema/tag-with-type % :human) @humans-data)]
    tag-humans-data))




