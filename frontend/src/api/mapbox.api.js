
export const MAPBOX_TOKEN = import.meta.env.VITE_MAPBOX_ACCESS_TOKEN;
const BASE_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places";

export const getUkrainianCities = async (query) => {
    if (!query) return [];

    const url =
        `${BASE_URL}/${encodeURIComponent(query)}.json` +
        `?access_token=${MAPBOX_TOKEN}` +
        `&country=UA` +
        `&types=place` +
        `&language=uk`;

    const res = await fetch(url);

    if (!res.ok) {
        throw new Error("Mapbox request failed");
    }

    const data = await res.json();

    return data.features.map((city) => ({
        label: city.text,
        fullName: city.place_name,
        coordinates: city.center,
        region: city.context?.find(c => c.id.includes("region"))?.text,
    }));
};