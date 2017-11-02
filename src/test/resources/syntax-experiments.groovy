		assert "boolean(//item)" << xpath
		assert "5000" == "//cost" << xpath
		assert "//signature-authority='Level1'" << xpath
		
		
		assert "boolean(//item)" * xpath
		assert "5000" == "//cost" * xpath
		assert "//signature-authority='Level1'" * xpath
		
		// seems a little ambiguous, since there is a common less than operation...
		assert "boolean(//item)" < xpath
		assert "5000" == "//cost" < xpath
		assert "//signature-authority='Level1'" < xpath
		
		assert "boolean(//item)">xpath
		assert "5000" == "//cost">xpath
		assert "//signature-authority='Level1'">xpath
		
		// this is a little ambiguous, so it might actually
		// fit the ambiguous nature of the handler...
		// but it's like a pipe!
		// intellij doesn't syntax highlight it well...
		// but perhaps I could throw in a default String override
		// where intellij would pick it up?  But that might be a bad thing.
		assert "boolean(//item)" | xpath
		assert "5000" == "//cost" | xpath
		assert "//signature-authority='Level1'" | xpath

		assert "boolean(//item)" | eval
		assert "5000" == "//cost" | eval
		assert "//signature-authority='Level1'" | eval
		
		// and the compact syntax is not bad either.
		assert "boolean(//item)"|xpath
		assert "5000" == "//cost"|xpath
		assert "//signature-authority='Level1'"|xpath
		
		assert "boolean(//item)" ** xpath
		assert "5000" == "//cost" ** xpath
		assert "//signature-authority='Level1'" ** xpath
		
		// I keep being drawn to this one...
		assert "boolean(//item)"[xpath]
		assert "5000" == "//cost"[xpath]
		assert "//signature-authority='Level1'"[xpath]
		
		// assuming we can override only the method that takes my handler
		assert "boolean(//item)".xpath(handler)
		assert "5000" == "//cost".xpath(handler)
		assert "//signature-authority='Level1'".xpath(handler)
		
		// would not work because xpath would need to be a class, which
		// should not be holding state in this case!
		class xpathHandler {}
		assert "boolean(//item)" as xpathHandler
		assert "5000" == "//cost" as xpathHandler
		assert "//signature-authority='Level1'" as xpathHandler
		
		// this actually seems kindof cool right at the moment...!
		// it has the potential for a somewhat sensible condensed syntax
		assert "boolean(//item)"/xpath
		assert "5000" == "//cost"/xpath
		assert "//signature-authority='Level1'"/xpath
		
		assert "boolean(//item)" / xpath
		assert "5000" == "//cost" / xpath
		assert "//signature-authority='Level1'" / xpath

		assert "boolean(//item)" / eval
		assert "5000" == "//cost" / eval
		assert "//signature-authority='Level1'" / eval
		
		assert "boolean(//item)" + xpath
		assert "5000" == "//cost" + xpath
		assert "//signature-authority='Level1'" + xpath
		
		// also kindof neat!
		assert "boolean(//item)" % xpath
		assert "5000" == "//cost" % xpath
		assert "//signature-authority='Level1'" % xpath

		assert "boolean(//item)"%xpath
		assert "5000" == "//cost"%xpath
		assert "//signature-authority='Level1'"%xpath
		
		// some are thinking of it more as an engine,
		// which is what it actually is.
		// Some are thinking of it as a description,
		// which is nice for expressiveness, but not
		// representative of what it actually is.
		//
		// I'd like to be able to declare the xpath variable
		// so that the assertions are not cumbersome.

		// this is also like a pipe
		assert "boolean(//item)" >> xpath
		assert "5000" == "//cost" >> xpath
		assert "//signature-authority='Level1'" >> xpath
		
		// or with xpathEngine declared
		def xpathEngine = new xpathHandler()
		assert "boolean(//item)" >> xpathEngine
		assert "5000" == "//cost" >> xpathEngine
		assert "//signature-authority='Level1'" >> xpathEngine
		
		assert "boolean(//item)"&xpath
		assert "5000" == "//cost"&xpath
		assert "//signature-authority='Level1'" >> xpath

		assert "boolean(//item)" ^ xpath
		assert "5000" == "//cost" ^ xpath
		assert "//signature-authority='Level1'" ^ xpath

		// not very beautiful!
		assert "boolean(//item)" - xpath
		assert "5000" == "//cost" - xpath
		assert "//signature-authority='Level1'" - xpath

		assert "boolean(//item)" <= xpath
		assert "5000" == "//cost" <= xpath
		assert "//signature-authority='Level1'" <= xpath
		
		
		
		
		
		
		
		