$.widget("ui.timespinner", $.ui.spinner, {
	options : {
		// 30 minutos
		step : 1800 * 1000,
		// 1 hora
		page : 60
	},

	_parse : function(value) {
		if (typeof value === "string") {
			// already a timestamp
			if (Number(value) == value) {
				return Number(value);
			}
			return +Globalize.parseDate(value);
		}
		return value;
	},

	_format : function(value) {
		return Globalize.format(new Date(value), "t");
	}
});