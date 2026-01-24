import { useEffect, useState } from "react";
import { TextField, Autocomplete } from "@mui/material";
import { getUkrainianCities } from "../api/mapbox.api";
import { useDebounce } from "../hooks/useDebounce";

const CityAutocomplete = ({ value, onChange }) => {
    const [input, setInput] = useState("");
    const [options, setOptions] = useState([]);

    const debouncedInput = useDebounce(input);

    useEffect(() => {
        const loadCities = async () => {
            try {
                const cities = await getUkrainianCities(debouncedInput);
                setOptions(cities);
            } catch (e) {
                console.error(e);
            }
        };

        loadCities();
    }, [debouncedInput]);

    return (
        <Autocomplete
            fullWidth
            options={options}
            getOptionLabel={(option) => option.label}
            onInputChange={(e, value) => setInput(value)}
            onChange={(e, value) => onChange(value)}
            renderInput={(params) => (
                <TextField
                    {...params}
                    label="City"
                    fullWidth
                    margin="normal"
                />
            )}
        />
    );
};

export default CityAutocomplete;
